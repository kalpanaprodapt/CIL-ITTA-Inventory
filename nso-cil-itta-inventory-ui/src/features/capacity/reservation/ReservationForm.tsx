import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { RESERVATION_LIFECYCLE_STAGE_OPTIONS, ReservationLifecycleStage } from './types';
import { validateReservation, cleanReservationValues } from './schema';
import { RESERVATION_DEFAULTS } from './types';
import type { ReservationFormValues, ReservationInput } from './types';

interface Props {
  initial?: ReservationFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ReservationInput) => void;
  onCancel?: () => void;
}

export function ReservationForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ReservationFormValues>({ ...RESERVATION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ReservationFormValues>(key: K, value: ReservationFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateReservation(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanReservationValues(values as Record<string, unknown>) as unknown as ReservationInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Reservation form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Resource Ref" htmlFor="resourceRef" error={errors.resourceRef} required={true}>
        <Input
          id="resourceRef"
          value={values.resourceRef ?? ''}
          maxLength={255}
          onChange={(v) => set('resourceRef', v)}
        />
      </FormField>
      <FormField label="Reserved For" htmlFor="reservedFor" error={errors.reservedFor} required={true}>
        <Input
          id="reservedFor"
          value={values.reservedFor ?? ''}
          maxLength={255}
          onChange={(v) => set('reservedFor', v)}
        />
      </FormField>
      <FormField label="Quantity" htmlFor="quantity" error={errors.quantity} required={true}>
        <NumberInput
          id="quantity"
          value={values.quantity}
          step={0.0001}
          onChange={(v) => set('quantity', v)}
        />
      </FormField>
      <FormField label="Start Date" htmlFor="startDate" error={errors.startDate} required={true}>
        <DateInput
          id="startDate"
          value={values.startDate ?? ''}
          onChange={(v) => set('startDate', v)}
        />
      </FormField>
      <FormField label="End Date" htmlFor="endDate" error={errors.endDate} required={false}>
        <DateInput
          id="endDate"
          value={values.endDate ?? ''}
          onChange={(v) => set('endDate', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={RESERVATION_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ReservationLifecycleStage | undefined)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}

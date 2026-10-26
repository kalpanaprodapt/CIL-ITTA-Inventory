import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';

import { validateFloor, cleanFloorValues } from './schema';
import { FLOOR_DEFAULTS } from './types';
import type { FloorFormValues, FloorInput } from './types';

interface Props {
  initial?: FloorFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: FloorInput) => void;
  onCancel?: () => void;
}

export function FloorForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<FloorFormValues>({ ...FLOOR_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof FloorFormValues>(key: K, value: FloorFormValues[K]) {
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
    const result = validateFloor(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanFloorValues(values as Record<string, unknown>) as unknown as FloorInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Floor form">
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
      <FormField label="Building Id" htmlFor="buildingId" error={errors.buildingId} required={true}>
        <Input
          id="buildingId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.buildingId ?? ''}
          onChange={(v) => set('buildingId', v)}
        />
      </FormField>
      <FormField label="Level" htmlFor="level" error={errors.level} required={false}>
        <NumberInput
          id="level"
          value={values.level}
          step={1}
          onChange={(v) => set('level', v)}
        />
      </FormField>
      <FormField label="Area Sqm" htmlFor="areaSqm" error={errors.areaSqm} required={false}>
        <NumberInput
          id="areaSqm"
          value={values.areaSqm}
          step={0.0001}
          onChange={(v) => set('areaSqm', v)}
        />
      </FormField>
      <FormField label="Load Capacity Kg" htmlFor="loadCapacityKg" error={errors.loadCapacityKg} required={false}>
        <NumberInput
          id="loadCapacityKg"
          value={values.loadCapacityKg}
          step={0.0001}
          onChange={(v) => set('loadCapacityKg', v)}
        />
      </FormField>
      <FormField label="Has Raised Floor" htmlFor="hasRaisedFloor" error={errors.hasRaisedFloor} required={false}>
        <Checkbox
          id="hasRaisedFloor"
          checked={Boolean(values.hasRaisedFloor)}
          onChange={(v) => set('hasRaisedFloor', v)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
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

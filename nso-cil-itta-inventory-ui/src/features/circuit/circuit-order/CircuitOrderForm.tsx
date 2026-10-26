import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CIRCUIT_ORDER_ORDER_STATUS_OPTIONS, CIRCUIT_ORDER_LIFECYCLE_STAGE_OPTIONS, CircuitOrderOrderStatus, CircuitOrderLifecycleStage } from './types';
import { validateCircuitOrder, cleanCircuitOrderValues } from './schema';
import { CIRCUITORDER_DEFAULTS } from './types';
import type { CircuitOrderFormValues, CircuitOrderInput } from './types';

interface Props {
  initial?: CircuitOrderFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CircuitOrderInput) => void;
  onCancel?: () => void;
}

export function CircuitOrderForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CircuitOrderFormValues>({ ...CIRCUITORDER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CircuitOrderFormValues>(key: K, value: CircuitOrderFormValues[K]) {
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
    const result = validateCircuitOrder(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCircuitOrderValues(values as Record<string, unknown>) as unknown as CircuitOrderInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Circuit Order form">
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
      <FormField label="Circuit Id" htmlFor="circuitId" error={errors.circuitId} required={false}>
        <Input
          id="circuitId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.circuitId ?? ''}
          onChange={(v) => set('circuitId', v)}
        />
      </FormField>
      <FormField label="Order Number" htmlFor="orderNumber" error={errors.orderNumber} required={true}>
        <Input
          id="orderNumber"
          value={values.orderNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('orderNumber', v)}
        />
      </FormField>
      <FormField label="Ordered On" htmlFor="orderedOn" error={errors.orderedOn} required={true}>
        <DateInput
          id="orderedOn"
          value={values.orderedOn ?? ''}
          onChange={(v) => set('orderedOn', v)}
        />
      </FormField>
      <FormField label="Requested Date" htmlFor="requestedDate" error={errors.requestedDate} required={false}>
        <DateInput
          id="requestedDate"
          value={values.requestedDate ?? ''}
          onChange={(v) => set('requestedDate', v)}
        />
      </FormField>
      <FormField label="Committed Date" htmlFor="committedDate" error={errors.committedDate} required={false}>
        <DateInput
          id="committedDate"
          value={values.committedDate ?? ''}
          onChange={(v) => set('committedDate', v)}
        />
      </FormField>
      <FormField label="Order Status" htmlFor="orderStatus" error={errors.orderStatus} required={false}>
        <Select
          id="orderStatus"
          value={values.orderStatus ?? ''}
          options={CIRCUIT_ORDER_ORDER_STATUS_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('orderStatus', (v || undefined) as CircuitOrderOrderStatus | undefined)}
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
          options={CIRCUIT_ORDER_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CircuitOrderLifecycleStage | undefined)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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

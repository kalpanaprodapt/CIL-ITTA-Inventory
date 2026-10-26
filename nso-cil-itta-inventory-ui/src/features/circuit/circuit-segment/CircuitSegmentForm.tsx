import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CIRCUIT_SEGMENT_CRITICALITY_OPTIONS, CircuitSegmentCriticality } from './types';
import { validateCircuitSegment, cleanCircuitSegmentValues } from './schema';
import { CIRCUITSEGMENT_DEFAULTS } from './types';
import type { CircuitSegmentFormValues, CircuitSegmentInput } from './types';

interface Props {
  initial?: CircuitSegmentFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CircuitSegmentInput) => void;
  onCancel?: () => void;
}

export function CircuitSegmentForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CircuitSegmentFormValues>({ ...CIRCUITSEGMENT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CircuitSegmentFormValues>(key: K, value: CircuitSegmentFormValues[K]) {
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
    const result = validateCircuitSegment(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCircuitSegmentValues(values as Record<string, unknown>) as unknown as CircuitSegmentInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Circuit Segment form">
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
      <FormField label="Circuit Id" htmlFor="circuitId" error={errors.circuitId} required={true}>
        <Input
          id="circuitId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.circuitId ?? ''}
          onChange={(v) => set('circuitId', v)}
        />
      </FormField>
      <FormField label="Segment Order" htmlFor="segmentOrder" error={errors.segmentOrder} required={true}>
        <NumberInput
          id="segmentOrder"
          value={values.segmentOrder}
          step={1}
          onChange={(v) => set('segmentOrder', v)}
        />
      </FormField>
      <FormField label="A Termination Id" htmlFor="aTerminationId" error={errors.aTerminationId} required={false}>
        <Input
          id="aTerminationId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aTerminationId ?? ''}
          onChange={(v) => set('aTerminationId', v)}
        />
      </FormField>
      <FormField label="Z Termination Id" htmlFor="zTerminationId" error={errors.zTerminationId} required={false}>
        <Input
          id="zTerminationId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zTerminationId ?? ''}
          onChange={(v) => set('zTerminationId', v)}
        />
      </FormField>
      <FormField label="Latency Ms" htmlFor="latencyMs" error={errors.latencyMs} required={false}>
        <NumberInput
          id="latencyMs"
          value={values.latencyMs}
          step={0.0001}
          onChange={(v) => set('latencyMs', v)}
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
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={CIRCUIT_SEGMENT_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as CircuitSegmentCriticality | undefined)}
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

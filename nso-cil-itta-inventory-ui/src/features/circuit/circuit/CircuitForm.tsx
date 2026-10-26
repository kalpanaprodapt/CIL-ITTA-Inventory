import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CIRCUIT_CIRCUIT_TYPE_OPTIONS, CIRCUIT_LIFECYCLE_STAGE_OPTIONS, CircuitCircuitType, CircuitLifecycleStage } from './types';
import { validateCircuit, cleanCircuitValues } from './schema';
import { CIRCUIT_DEFAULTS } from './types';
import type { CircuitFormValues, CircuitInput } from './types';

interface Props {
  initial?: CircuitFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CircuitInput) => void;
  onCancel?: () => void;
}

export function CircuitForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CircuitFormValues>({ ...CIRCUIT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CircuitFormValues>(key: K, value: CircuitFormValues[K]) {
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
    const result = validateCircuit(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCircuitValues(values as Record<string, unknown>) as unknown as CircuitInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Circuit form">
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
          value={values.circuitId ?? ''}
          maxLength={255}
          onChange={(v) => set('circuitId', v)}
        />
      </FormField>
      <FormField label="Circuit Type" htmlFor="circuitType" error={errors.circuitType} required={false}>
        <Select
          id="circuitType"
          value={values.circuitType ?? ''}
          options={CIRCUIT_CIRCUIT_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('circuitType', (v || undefined) as CircuitCircuitType | undefined)}
        />
      </FormField>
      <FormField label="Bandwidth Mbps" htmlFor="bandwidthMbps" error={errors.bandwidthMbps} required={true}>
        <NumberInput
          id="bandwidthMbps"
          value={values.bandwidthMbps}
          step={1}
          onChange={(v) => set('bandwidthMbps', v)}
        />
      </FormField>
      <FormField label="Provider Id" htmlFor="providerId" error={errors.providerId} required={false}>
        <Input
          id="providerId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.providerId ?? ''}
          onChange={(v) => set('providerId', v)}
        />
      </FormField>
      <FormField label="A Site Id" htmlFor="aSiteId" error={errors.aSiteId} required={false}>
        <Input
          id="aSiteId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aSiteId ?? ''}
          onChange={(v) => set('aSiteId', v)}
        />
      </FormField>
      <FormField label="Z Site Id" htmlFor="zSiteId" error={errors.zSiteId} required={false}>
        <Input
          id="zSiteId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zSiteId ?? ''}
          onChange={(v) => set('zSiteId', v)}
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
          options={CIRCUIT_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as CircuitLifecycleStage | undefined)}
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

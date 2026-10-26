import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CIRCUIT_PROVIDER_PROVIDER_TYPE_OPTIONS, CIRCUIT_PROVIDER_CRITICALITY_OPTIONS, CircuitProviderProviderType, CircuitProviderCriticality } from './types';
import { validateCircuitProvider, cleanCircuitProviderValues } from './schema';
import { CIRCUITPROVIDER_DEFAULTS } from './types';
import type { CircuitProviderFormValues, CircuitProviderInput } from './types';

interface Props {
  initial?: CircuitProviderFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CircuitProviderInput) => void;
  onCancel?: () => void;
}

export function CircuitProviderForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CircuitProviderFormValues>({ ...CIRCUITPROVIDER_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CircuitProviderFormValues>(key: K, value: CircuitProviderFormValues[K]) {
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
    const result = validateCircuitProvider(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCircuitProviderValues(values as Record<string, unknown>) as unknown as CircuitProviderInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Circuit Provider form">
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
      <FormField label="Provider Name" htmlFor="providerName" error={errors.providerName} required={true}>
        <Input
          id="providerName"
          value={values.providerName ?? ''}
          maxLength={255}
          onChange={(v) => set('providerName', v)}
        />
      </FormField>
      <FormField label="Account Number" htmlFor="accountNumber" error={errors.accountNumber} required={false}>
        <Input
          id="accountNumber"
          value={values.accountNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('accountNumber', v)}
        />
      </FormField>
      <FormField label="Noc" htmlFor="noc" error={errors.noc} required={false}>
        <Input
          id="noc"
          value={values.noc ?? ''}
          maxLength={255}
          onChange={(v) => set('noc', v)}
        />
      </FormField>
      <FormField label="Escalation Email" htmlFor="escalationEmail" error={errors.escalationEmail} required={false}>
        <Input
          id="escalationEmail"
          value={values.escalationEmail ?? ''}
          maxLength={255}
          onChange={(v) => set('escalationEmail', v)}
        />
      </FormField>
      <FormField label="Provider Type" htmlFor="providerType" error={errors.providerType} required={false}>
        <Select
          id="providerType"
          value={values.providerType ?? ''}
          options={CIRCUIT_PROVIDER_PROVIDER_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('providerType', (v || undefined) as CircuitProviderProviderType | undefined)}
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
          options={CIRCUIT_PROVIDER_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as CircuitProviderCriticality | undefined)}
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

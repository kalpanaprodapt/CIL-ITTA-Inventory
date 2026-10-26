import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { SERVICE_DEPENDENCY_DEPENDENCY_TYPE_OPTIONS, SERVICE_DEPENDENCY_CRITICALITY_OPTIONS, ServiceDependencyDependencyType, ServiceDependencyCriticality } from './types';
import { validateServiceDependency, cleanServiceDependencyValues } from './schema';
import { SERVICEDEPENDENCY_DEFAULTS } from './types';
import type { ServiceDependencyFormValues, ServiceDependencyInput } from './types';

interface Props {
  initial?: ServiceDependencyFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ServiceDependencyInput) => void;
  onCancel?: () => void;
}

export function ServiceDependencyForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ServiceDependencyFormValues>({ ...SERVICEDEPENDENCY_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ServiceDependencyFormValues>(key: K, value: ServiceDependencyFormValues[K]) {
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
    const result = validateServiceDependency(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanServiceDependencyValues(values as Record<string, unknown>) as unknown as ServiceDependencyInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Service Dependency form">
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
      <FormField label="Service Id" htmlFor="serviceId" error={errors.serviceId} required={true}>
        <Input
          id="serviceId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.serviceId ?? ''}
          onChange={(v) => set('serviceId', v)}
        />
      </FormField>
      <FormField label="Depends On Id" htmlFor="dependsOnId" error={errors.dependsOnId} required={true}>
        <Input
          id="dependsOnId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.dependsOnId ?? ''}
          onChange={(v) => set('dependsOnId', v)}
        />
      </FormField>
      <FormField label="Dependency Type" htmlFor="dependencyType" error={errors.dependencyType} required={false}>
        <Select
          id="dependencyType"
          value={values.dependencyType ?? ''}
          options={SERVICE_DEPENDENCY_DEPENDENCY_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('dependencyType', (v || undefined) as ServiceDependencyDependencyType | undefined)}
        />
      </FormField>
      <FormField label="Impact Score" htmlFor="impactScore" error={errors.impactScore} required={false}>
        <NumberInput
          id="impactScore"
          value={values.impactScore}
          step={0.0001}
          onChange={(v) => set('impactScore', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={SERVICE_DEPENDENCY_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ServiceDependencyCriticality | undefined)}
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

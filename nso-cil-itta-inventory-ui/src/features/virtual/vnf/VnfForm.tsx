import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { VNF_LIFECYCLE_STAGE_OPTIONS, VNF_CRITICALITY_OPTIONS, VnfLifecycleStage, VnfCriticality } from './types';
import { validateVnf, cleanVnfValues } from './schema';
import { VNF_DEFAULTS } from './types';
import type { VnfFormValues, VnfInput } from './types';

interface Props {
  initial?: VnfFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: VnfInput) => void;
  onCancel?: () => void;
}

export function VnfForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<VnfFormValues>({ ...VNF_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof VnfFormValues>(key: K, value: VnfFormValues[K]) {
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
    const result = validateVnf(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanVnfValues(values as Record<string, unknown>) as unknown as VnfInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Vnf form">
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
      <FormField label="Vnf Name" htmlFor="vnfName" error={errors.vnfName} required={true}>
        <Input
          id="vnfName"
          value={values.vnfName ?? ''}
          maxLength={255}
          onChange={(v) => set('vnfName', v)}
        />
      </FormField>
      <FormField label="Vnf Type" htmlFor="vnfType" error={errors.vnfType} required={true}>
        <Input
          id="vnfType"
          value={values.vnfType ?? ''}
          maxLength={255}
          onChange={(v) => set('vnfType', v)}
        />
      </FormField>
      <FormField label="Flavor" htmlFor="flavor" error={errors.flavor} required={false}>
        <Input
          id="flavor"
          value={values.flavor ?? ''}
          maxLength={255}
          onChange={(v) => set('flavor', v)}
        />
      </FormField>
      <FormField label="Vcpu" htmlFor="vcpu" error={errors.vcpu} required={false}>
        <NumberInput
          id="vcpu"
          value={values.vcpu}
          step={1}
          onChange={(v) => set('vcpu', v)}
        />
      </FormField>
      <FormField label="Memory Gb" htmlFor="memoryGb" error={errors.memoryGb} required={false}>
        <NumberInput
          id="memoryGb"
          value={values.memoryGb}
          step={1}
          onChange={(v) => set('memoryGb', v)}
        />
      </FormField>
      <FormField label="Instance Count" htmlFor="instanceCount" error={errors.instanceCount} required={false}>
        <NumberInput
          id="instanceCount"
          value={values.instanceCount}
          step={1}
          onChange={(v) => set('instanceCount', v)}
        />
      </FormField>
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={VNF_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as VnfLifecycleStage | undefined)}
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
          options={VNF_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as VnfCriticality | undefined)}
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

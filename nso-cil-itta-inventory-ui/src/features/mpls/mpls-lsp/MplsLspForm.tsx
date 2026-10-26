import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { MPLS_LSP_PATH_TYPE_OPTIONS, MPLS_LSP_CRITICALITY_OPTIONS, MplsLspPathType, MplsLspCriticality } from './types';
import { validateMplsLsp, cleanMplsLspValues } from './schema';
import { MPLSLSP_DEFAULTS } from './types';
import type { MplsLspFormValues, MplsLspInput } from './types';

interface Props {
  initial?: MplsLspFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: MplsLspInput) => void;
  onCancel?: () => void;
}

export function MplsLspForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<MplsLspFormValues>({ ...MPLSLSP_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof MplsLspFormValues>(key: K, value: MplsLspFormValues[K]) {
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
    const result = validateMplsLsp(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanMplsLspValues(values as Record<string, unknown>) as unknown as MplsLspInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Mpls Lsp form">
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
      <FormField label="Lsp Name" htmlFor="lspName" error={errors.lspName} required={true}>
        <Input
          id="lspName"
          value={values.lspName ?? ''}
          maxLength={255}
          onChange={(v) => set('lspName', v)}
        />
      </FormField>
      <FormField label="Head End Id" htmlFor="headEndId" error={errors.headEndId} required={true}>
        <Input
          id="headEndId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.headEndId ?? ''}
          onChange={(v) => set('headEndId', v)}
        />
      </FormField>
      <FormField label="Tail End Id" htmlFor="tailEndId" error={errors.tailEndId} required={true}>
        <Input
          id="tailEndId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.tailEndId ?? ''}
          onChange={(v) => set('tailEndId', v)}
        />
      </FormField>
      <FormField label="Bandwidth Mbps" htmlFor="bandwidthMbps" error={errors.bandwidthMbps} required={false}>
        <NumberInput
          id="bandwidthMbps"
          value={values.bandwidthMbps}
          step={1}
          onChange={(v) => set('bandwidthMbps', v)}
        />
      </FormField>
      <FormField label="Setup Priority" htmlFor="setupPriority" error={errors.setupPriority} required={false}>
        <NumberInput
          id="setupPriority"
          value={values.setupPriority}
          step={1}
          onChange={(v) => set('setupPriority', v)}
        />
      </FormField>
      <FormField label="Path Type" htmlFor="pathType" error={errors.pathType} required={false}>
        <Select
          id="pathType"
          value={values.pathType ?? ''}
          options={MPLS_LSP_PATH_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('pathType', (v || undefined) as MplsLspPathType | undefined)}
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
          options={MPLS_LSP_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as MplsLspCriticality | undefined)}
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
